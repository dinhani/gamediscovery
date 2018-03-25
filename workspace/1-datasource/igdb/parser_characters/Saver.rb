require 'jrjackson'
require './../../_shared/output/CSV.rb'

class Saver

  def initialize(output_file)
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'id_igdb', "name", 'attributes'].to_java(:string))
  end

  def save(game)
    @writer.writeNext([game.id, game.id_specific, game.name, JrJackson::Json.dump(game.attributes)].to_java(:string))    
  end

  def shutdown
    @writer.close
  end
end
