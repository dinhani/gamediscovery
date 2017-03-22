require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

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
    html_doc = org.jsoup.Jsoup.parse(html)

    # parse
    game = Entity.new
    game.name = parse_name(html_doc)
    game.id = parse_id(game.name)
    game.id_specific = parse_id_specific(filename)

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
    html_doc.select(".game-name").text
  end
end
