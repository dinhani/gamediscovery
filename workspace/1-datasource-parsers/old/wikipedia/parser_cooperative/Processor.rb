require 'uri'
require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor
  def stateful?
    false
  end

  def process(html_row)
    name = html_row.css("td:first a")[0].text
    puts name
    id_wikipedia = URI::unescape html_row.css("td:first a")[0].attribute("href").value[6..-1].downcase

    entity = Entity.new
    entity.id = ID::parse(name)
    entity.id_specific = id_wikipedia
    entity.attributes[:number_of_players] = [html_row.css("td")[4].text.downcase]
    entity.attributes[:cooperative_type] = html_row.css("td")[5].text.downcase.split(",").map(&:strip)
    entity.attributes[:screen_type] = html_row.css("td")[6].text.downcase.split(",").map(&:strip)

    entity
  end
end
