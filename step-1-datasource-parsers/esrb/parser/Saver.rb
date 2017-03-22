require './../../_shared/output/CSV.rb'
java_import 'java.util.HashSet'

class Saver

  def initialize(output_file)
    # concurrency
    @mutex = Mutex.new
    @saved = HashSet.new

    # csv
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'categories', 'classification'].to_java(:string))
  end

  def save(game)
    # if already saved, do not save again
    @mutex.synchronize do
      if @saved.contains(game.id)
        return
      end

      # save
      @saved.add(game.id)
      @writer.writeNext([game.id, "{#{game.categories.join(',')}}", game.attributes[:classification]].to_java(:string))
    end
  end

  def shutdown
    @writer.close()
  end
end
