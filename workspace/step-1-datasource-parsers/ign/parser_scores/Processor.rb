require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor

  def stateful?
    false
  end

  def process(line)
    # split
    line_parts = line.split(";")

    # process
    game = Entity.new
    game.id = parse_id(line_parts)
    game.attributes[:score] = parse_score(line_parts)

    game
  end

  def parse_id(line_parts)
    ID.parse(line_parts[0])
  end

  def parse_score(line_parts)
    line_parts[2].gsub(",", ".").to_f
  end
end
