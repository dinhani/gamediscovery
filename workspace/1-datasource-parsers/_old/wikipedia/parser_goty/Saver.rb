require './../../_shared/output/CSV.rb'

class Saver

  def initialize(output_file)
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'id_wikipedia'].to_java(:string))
    @writer.flush()
  end

  def save(game)
    @writer.writeNext([game.id, game.id_specific].to_java(:string))
  end

  def shutdown
    @writer.close
  end
end
