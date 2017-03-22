require 'jrjackson'
require './../../_shared/output/CSV.rb'

java_import 'java.util.HashSet'

class Saver

  def initialize(output_dir)
    @mutex = Mutex.new
    @saved = HashSet.new

    @writer_en = CSV::create("#{output_dir}/articles_en.csv")
    @writer_en.writeNext(['id', 'id_wikipedia', "word_count", 'summary', 'attributes'].to_java(:string))
    @writer_en.flush()

    @writer_pt = CSV::create("#{output_dir}/articles_pt.csv")
    @writer_pt.writeNext(['id', 'id_wikipedia', "word_count", 'summary', 'attributes'].to_java(:string))
    @writer_pt.flush()
  end

  def save(entity)
    # save
    @mutex.synchronize do
      # choose writer
      if(entity.attributes[:lang] == 'en')
        writer = @writer_en
      else
        writer = @writer_pt
      end

      # do save
      entity.attributes.delete(:lang)      
      writer.writeNext([entity.id, entity.id_specific, JrJackson::Json.dump(entity.word_count), entity.summary, JrJackson::Json.dump(entity.attributes)].to_java(:string))
      writer.flush()
    end
  end

  def shutdown
    @writer_en.close
    @writer_pt.close
  end
end
