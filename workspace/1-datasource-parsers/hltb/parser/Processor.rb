require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

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
    return nil if game.id.empty? or game.id.include? "cdata"

    # attributes
    game.attributes = parse_attributes(html_doc)

    # return
    game
  end

  # ============================================================================
  # PARSING (SPECIFIC)
  # ============================================================================
  private def parse_id(html_doc)
    name = html_doc.select(".profile_header").text
    return ID::parse(name)
  end

  private def parse_attributes(html_doc)
    attributes = {}

    # dlc
    attributes[:has_dlc] = html_doc.select("h3").text.include? "Additional Content:"

    # time to beat
    attributes[:duration] = html_doc.select(".game_times li div")[0].text
      .gsub("½", ".5")
      .gsub(" Hours ", "")

    attributes
  end
end
