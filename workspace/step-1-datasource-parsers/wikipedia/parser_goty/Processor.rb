require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor
  def stateful?
    false
  end

  def process(html_link)
    entity = Entity.new
    entity.id = parse_id(html_link)
    entity.id_specific = parse_id_wikipedia(html_link)

    entity
  end

  private def parse_id(html_link)
    ID::parse(html_link.text)
  end

  private def parse_id_wikipedia(html_link)
    URI::unescape html_link.attr("href")[6..-1].downcase
  end
end
