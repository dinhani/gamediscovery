require 'uri'
require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor
  def stateful?
    false
  end

  def process(game)
    entity = Entity.new

    entity.id = parse_id(game)
    entity.summary = parse_summary(game)
    entity.attributes = parse_attributes(game)

    entity
  end

  private def parse_id(game)
    name = game.select("Name").text
    ID::parse(name)
  end

  private def parse_summary(game)
    overview = game.select("Overview")
    paragraphs = overview.text.split("\n")
    return paragraphs[0].strip unless paragraphs.nil? or paragraphs.empty?
  end

  private def parse_attributes(game)
    attributes = {}
    attributes[:genre] = game.select("Genres").text.split(";").map(&:strip)
    attributes[:release_year] = game.select("ReleaseYear").text.strip
    attributes[:developer] = game.select("Developer").text.strip
    attributes[:publisher] = game.select("Publisher").text.strip

    attributes[:video] = game.select("VideoURL").text.strip   

    attributes
  end
end
