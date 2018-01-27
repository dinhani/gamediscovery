require 'nokogiri'
require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor

  def initialize
    @parsed = {}
  end

  def stateful?
    false
  end

  # ============================================================================
  # PARSING (GENERAL)
  # ============================================================================
  def process(content)
    # parse xml
    filename, content = content
    xml_doc = Nokogiri::XML(content)
    name = parse_name(xml_doc)

    # discard if don't have a name
    return if name.nil?

    # create Game
    game = Entity.new
    game.id = ID::parse(name)
    game.attributes = parse_attributes(xml_doc)

    # merge
    if @parsed.has_key? game.id
      nil
    else
      game
    end
  end

  # ============================================================================
  # PARSING (SPECIFIC)
  # ============================================================================
  private def parse_name(xml_doc)
    elements = xml_doc.xpath("//GameTitle")
    if not elements.empty?
      elements.first.text
    else
      nil
    end
  end

  private def parse_id(name)
    name.downcase.gsub(" ", "_")
  end

  private def parse_attributes(xml_doc)
    attributes = {}
    attributes[:number_of_players] = [xml_doc.css("Players").text.gsub(/[^0-9]/, "")]
    attributes[:aliases] = xml_doc.css("title").map {|t| t.text}

    attributes
    #xml_doc.to_xml(:save_with => Nokogiri::XML::Node::SaveOptions::AS_XML | Nokogiri::XML::Node::SaveOptions::NO_DECLARATION).gsub("\n", "").strip
  end

end
