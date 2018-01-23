require './../../_shared/output/CSV.rb'
require 'jrjackson'

class Saver

  def initialize(output_file)
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'id_wikipedia', 'attributes'].to_java(:string))
  end

  def save(article)
    # save
    attributes_json = JrJackson::Json.dump(article.attributes)
    @writer.writeNext([article.id, article.id_specific, attributes_json].to_java(:string))
    @writer.flush()
  end

  def shutdown
    @writer.close()
  end
end
