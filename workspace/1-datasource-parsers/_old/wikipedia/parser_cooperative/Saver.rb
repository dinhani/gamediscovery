require 'jrjackson'
require './../../_shared/output/CSV.rb'

class Saver

  def initialize(output_file)
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'id_wikipedia', 'attributes'].to_java(:string))
    @writer.flush()
  end

  def save(game)
    attributes_json = JrJackson::Json.dump(game.attributes)
    @writer.writeNext([game.id, game.id_specific, attributes_json].to_java(:string))
  end

  def shutdown
    @writer.close
  end
end
