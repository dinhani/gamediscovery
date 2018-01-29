require './../../_shared/output/CSV.rb'
require 'jrjackson'

class Saver

  def initialize(output_file)
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'id_wikipedia', 'categories'].to_java(:string))
  end

  def save(article)
    @writer.writeNext([article.id, article.id_specific, "{#{article.categories.join(',').gsub(",,", ",")}}"].to_java(:string))    
  end

  def shutdown
    @writer.close()
  end
end
