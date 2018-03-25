require 'jrjackson'
require './../../_shared/output/CSV.rb'

java_import 'java.util.HashSet'


class Saver

  def initialize(output_file)
    # saved entities to avoid repeated ids
    @saved = HashSet.new

    # csv header
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'summary', 'attributes'].to_java(:string))
    @writer.flush()
  end

  def save(game)
    # check saved
    if @saved.contains(game.id)
      return
    end


    # write
    attributes_json = JrJackson::Json.dump(game.attributes)
    @writer.writeNext([game.id, game.summary, attributes_json].to_java(:string))

    # mark as saved
    @saved.add(game.id)
  end

  def shutdown
    @writer.close
  end
end
