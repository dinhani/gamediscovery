require 'nokogiri'

require './../../_shared/processing//Entity.rb'
require './../../_shared/processing//ID.rb'

class Processor

  # ============================================================================
  # STATEFUL INDICATOR
  # ============================================================================
  def stateful?
    false
  end

  # ============================================================================
  # PARSING
  # ============================================================================
  def process(content)
    # prepare html
    filename, html = content
    html_doc = Nokogiri::HTML(html)

    # parse
    game = Entity.new
    game.name = parse_name(html_doc)
    game.id = parse_id(game.name)
    game.id_specific = parse_id_specific(filename)
    game.attributes  = parse_games(html_doc)

    # return
    game
  end

  private def parse_id(name)
    ID::parse(name)
  end

  private def parse_id_specific(filename)
    filename.partition(".")[0]
  end

  private def parse_name(html_doc)
    html_doc.css("h1")[0].text.gsub(" (Character)", "")
  end

  private def parse_games(html_doc)
    attributes = {}
    attributes["games"] = html_doc.css("[data-pageid=game]").map{|game| game.text}

    attributes
  end
end
