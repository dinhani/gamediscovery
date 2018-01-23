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
    return if game.id.nil?

    # attributes
    game.attributes = parse_attributes(html_doc)

    # return
    game
  end

  # ============================================================================
  # PARSING (SPECIFIC)
  # ============================================================================
  private def parse_id(html_doc)
    name = html_doc.select("h2").text.strip
    return ID::parse(name)
  end

  private def parse_attributes(html_doc)
    attributes = {}

    # trophy descriptions
    descriptions = html_doc.select(".subheader")
    attributes["trophies"] = descriptions.map do |description|
      description.text.strip
    end

    attributes
  end
end
