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
    games = html_doc.css(".media-game").map do |html_fragment|
      game = Entity.new
      game.id = parse_id(html_fragment)
      game.attributes[:score] = parse_score(html_fragment)

      game
    end

    # return
    games
  end

  private def parse_id(html_fragment)
    name = html_fragment.css(".media-title").text.gsub("Review", "")
    ID.parse(name)
  end

  private def parse_score(html_fragment)
    score = html_fragment.css(".media-well--review-gs strong").text.to_f
    score
  end
end
