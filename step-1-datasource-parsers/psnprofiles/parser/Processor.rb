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
    headers = html_doc.select(".title h3")

    # checks
    return nil if headers.nil?
    return nil if headers.empty?

    # parse
    name = headers[0].text.gsub("Trophies", "").strip
    return nil if name == "Main"
    return ID::parse(name)
  end

  private def parse_attributes(html_doc)
    attributes = {}

    # try first table
    cells = html_doc.select("table")[0].select("tr td:nth-child(2)")
    # try second table
    cells = html_doc.select("table")[1].select("tr td:nth-child(2)") if cells.size <= 1
    # try third table
    cells = html_doc.select("table")[2].select("tr td:nth-child(2)") if cells.size <= 1

    # parse thropy info
    attributes["trophies"] = cells.map do |cell|
      name = cell.select("a").text
      description = cell.text[name.size..-1]

      description.strip
    end

    attributes
  end
end
