require 'jrjackson'
require './../../_shared/output/CSV.rb'

class Saver

  def initialize(output_file)
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', "aliases", 'word_count'].to_java(:string))
    @writer.flush()

    @games = {}
  end

  # ============================================================================
  # SAVE
  # ============================================================================
  def save(games)
    games.each do |game|
      # store first game for each id
      if not @games.key? game.id
        @games[game.id] = game
      end

      # merge word count
      @games[game.id].word_count.merge!(game.word_count) {|k, old_v, new_v| old_v + new_v}
    end
  end

  # ============================================================================
  # SHUTDOWN
  # ============================================================================
  def shutdown
    # write to file
    @games.values.each do |game|
      @writer.writeNext([game.id, "{#{game.aliases}}", JrJackson::Json.dump(game.word_count)].to_java(:string))
    end

    # close
    @writer.close
  end
end
