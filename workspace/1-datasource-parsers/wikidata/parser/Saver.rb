require './../../_shared/output/CSV.rb'

class Saver

  # ============================================================================
  # INIT
  # ============================================================================
  def initialize(output_dir)
    # init writers
    items_filename = java.nio.file.Paths.get(output_dir, "entities.csv").toString()
    @item_writer = CSV::create(items_filename)
    @item_writer.writeNext(['id', 'id_wikipedia', 'id_wikidata', 'link', 'link_pt', 'name', 'attributes'].to_java(:string))

    attributes_filename = java.nio.file.Paths.get(output_dir, "attributes.csv").toString()
    @attribute_writer = CSV::create(attributes_filename)
    @attribute_writer.writeNext(['id', 'name'].to_java(:string))
  end

  # ============================================================================
  # SAVE
  # ============================================================================
  def save(entity)
    if(entity.class == Item)
        save_item(entity)
    else
        save_attribute(entity)
    end
  end

  private def save_item(item)
    attributes_json = JrJackson::Json.dump(item.attributes)
    @item_writer.writeNext([item.id[0..254], item.id_wikipedia[0..254], item.id_specific, item.link[0..254], item.link_pt[0..254], item.name[0..254], attributes_json].to_java(:string))
    @item_writer.flush()
  end

  private def save_attribute(attribute)
    @attribute_writer.writeNext([attribute.id, attribute.name].to_java(:string))
    @attribute_writer.flush()
  end

  # ============================================================================
  # SHUTDOWN
  # ============================================================================
  def shutdown
    @item_writer.close()
    @attribute_writer.close()
  end
end
