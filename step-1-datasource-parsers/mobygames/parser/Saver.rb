require 'jrjackson'
require './../../_shared/output/CSV.rb'

class Saver
  def initialize(output_file)
    @writer = CSV::create(output_file)
  end

  def save(game)
    @writer.writeNext([game.id, JrJackson::Json.dump(game.attributes)].to_java(:string))
  end

  def shutdown
    @writer.close
  end
end
