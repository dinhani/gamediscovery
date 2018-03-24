require 'jrjackson'
require './../../_shared/output/CSV.rb'

class Saver

  def initialize(output_file)
    # csv
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'score'].to_java(:string))

    # data
    @games = {}
  end

  def save(games)
    # save is performed only in shutdown
    # save only the highest score

    # check if exists
    games.each do |game|
      if @games.key? game.id
        if game.attributes[:score] > @games[game.id].attributes[:score]
          @games[game.id] = game
        end
      # first time
      else
        @games[game.id] = game
      end
    end
  end

  def shutdown
    @games.values.each do |game|
      @writer.writeNext([game.id, game.attributes[:score].to_s].to_java(:string))
    end
    @writer.close()
  end
end
