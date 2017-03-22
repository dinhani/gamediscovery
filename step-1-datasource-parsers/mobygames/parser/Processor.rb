require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'
require 'active_support/inflector'


class Processor
  def stateful?
    false
  end

  # ============================================================================
  # PARSING (GENERAL)
  # ============================================================================
  def process(content)
    # parse html
    filename, html = content
    html_doc = org.jsoup.Jsoup.parse(html)

    # id
    game = Entity.new
    game.id = parse_id(html_doc)
    return nil if game.id.empty?

    # attributes
    game.attributes = parse_attributes(html_doc)

    # return
    game
  end

  # ============================================================================
  # PARSING (SPECIFIC)
  # ============================================================================
  private def parse_id(html_doc)
    name = html_doc.select(".niceHeaderTitle > a").text
    ID::parse(name)
  end

  private def parse_attributes(html_doc)
    attributes = {}
    attributes = parse_game_attributes(html_doc, attributes)
    attributes = parse_game_groups(html_doc, attributes)

    attributes
  end

  private def parse_game_attributes(html_doc, attributes)
    # get core elements
    core_elements = html_doc.select("#coreGameRelease div")

    # get genre elements and exclude first div representing rating if necessary
    genre_elements = html_doc.select("#coreGameGenre div")
    genre_elements = genre_elements[1..-1] if genre_elements.size % 2 == 1

    # iterate core and genre elements
    (core_elements + genre_elements).each_slice(2) do |key_element, value_element|
      case key_element.text.strip
        when "Published by"
          key = :publisher
        when "Developed by"
          key = :developer
        when "Released"
          key = :release_date
        when "Platform", "Platforms"
          key = :platforms # keep it in plural to match dbpedia
        when "Genre", "Gameplay"
          key = :genre
        when "Narrative", "Setting", "Sport"
          key = :theme
        when "Perspective", "Art", "Visual"
          key = :graphics
        when "Vehicular"
          key = :vehicle
        # ?
        when "Pacing"
          key = :pacing
        when "Educational"
          key = :educational
        when "Misc"
          key = :misc
        else
          key = nil
      end

      # parse values and add
      values = value_element.select("a").map{|el| el.text.gsub("\u00A0", " ")}#
      add_to_attributes(attributes, key, values)
    end

    return attributes
  end

  private def parse_game_groups(html_doc, attributes)
    groups = html_doc.select("h2 + ul li a").map(&:text)

    groups.each do |group|
      # creature
      if group.start_with? "Animals"
        key = :creature
        value = group.rpartition(":")[-1].strip.split("/").map(&:strip).map(&:singularize)
      # engine
      elsif group.start_with? "3D Engine" or group.start_with? "Physics Engine"  or group.start_with? "Graphics Engine"  or group.start_with? "Middleware" or group.start_with? "Game Engine"
        key = :engine
        value = group.rpartition(":")[-1].strip
      # genre
      elsif group.start_with? "Genre"
        key = :genre
        value = group.rpartition(":")[-1].strip
      # graphics
      elsif group.start_with? "Visual technique"
        key = :graphics
        value = group.rpartition(":")[-1].strip
      # location
      elsif group.start_with? "Setting"
        key = :location
        value = group.rpartition(":")[-1].strip
        if value.start_with? "City - "
          value = value.gsub("City - ", "")
        end
      # mechanics
      elsif group.start_with? "Gameplay Feature" or group.start_with? "Gameplay feature" or group.start_with? "Game Feature"
        key = :mechanics
        value = group.rpartition(":")[-1].strip
      # series
      elsif group.end_with? "series"
        key = :series
        value = group.gsub(" series", "")
      # theme
      elsif group.start_with? "Theme" or group.start_with? "Inspiration" or group.start_with? "Historical conflict" or group.end_with? "licensees"
        key = :theme
        value = group.rpartition(":")[-1].gsub("licensees", "").strip
      # vehicle
      elsif group.start_with? "Vehicle:" or group.start_with? "Automobile:" or group.start_with? "Aircraft:" or group.start_with? "Tank:"
        key = :vehicle
        value = group.rpartition(":")[-1].strip
      #else
      #  key = :other
      #  value = group.strip
      end

      add_to_attributes(attributes, key, value)
    end
    return attributes
  end

  private def add_to_attributes(attributes, key, value)
    # check key
    return if key.nil?

    # create array if necessary
    attributes[key] = [] unless attributes.key? key

    # add element to created array
    if value.class == Array
      attributes[key] = attributes[key] + value
    else
      attributes[key] << value
    end
  end
end
