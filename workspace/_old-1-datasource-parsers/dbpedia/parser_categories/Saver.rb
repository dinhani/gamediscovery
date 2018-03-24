require 'jrjackson'
require './../../_shared/output/CSV.rb'

class Saver

  def initialize(output_file)
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'id_wikipedia', 'categories'].to_java(:string))
  end

  def save(category)
    @writer.writeNext([category.id, category.id_specific, "{#{category.categories.join(',')}}"].to_java(:string))
  end

  def shutdown
    @writer.close()
  end
end
