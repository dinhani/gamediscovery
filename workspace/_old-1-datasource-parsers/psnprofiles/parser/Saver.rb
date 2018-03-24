require 'jrjackson'
require './../../_shared/output/CSV.rb'
java_import 'java.util.HashSet'

class Saver
  def initialize(output_file)
    @saved = HashSet.new
    @mutex = Mutex.new

    @writer = CSV::create(output_file)
    @writer.writeNext(["id", "attributes"].to_java(:string))
  end

  def save(game)
    @mutex.synchronize do
      # check already saved
      if @saved.contains(game.id)
        return
      end

      # save
      @writer.writeNext([game.id, JrJackson::Json.dump(game.attributes)].to_java(:string))

      # mark as saved
      @saved.add(game.id)
    end
  end

  def shutdown
    @writer.close
  end
end
