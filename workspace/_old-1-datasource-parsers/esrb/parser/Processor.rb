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
    game.id = parse_id(html_doc)
    game.categories = parse_categories(html_doc)
    game.attributes[:classification] = parse_classification(html_doc)

    # return
    return nil if game.id.nil? or game.id.empty?
    return game
  end

  private def parse_id(html_doc)
    name = html_doc.select("#Content_Synopsis_Data_Synopsis_Title").text
    ID.parse(name)
  end

  private def parse_categories(html_doc)
    html_doc.select("#Content_Synopsis_Data_Synopsis_Content").text.split(",").map(&:strip)
  end

  private def parse_classification(html_doc)
    begin
      filename = html_doc.select("#Content_Synopsis_Data_RatingImg").attr("src").value
      File.basename(filename, ".*")
    rescue
      ""
    end
  end

end
