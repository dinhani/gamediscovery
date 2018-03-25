require 'jrjackson'
require './../../_shared/output/CSV.rb'

class Saver

  # ============================================================================
  # INITIALIZATION
  # ============================================================================
  def initialize(output_file)
    @writer = CSV::create(output_file)
    @writer.writeNext(['id', 'id_igdb', 'name', 'type', 'attributes'].to_java(:string))
  end

  # ============================================================================
  # ADDING DATA
  # ============================================================================
  def save(entities)
    entities.each do |entity|
      @writer.writeNext([entity.id, entity.id_specific, entity.name, entity.type, JrJackson::Json.dump(entity.attributes)].to_java(:string))
    end
  end

  # ============================================================================
  # FINALIZATION
  # ============================================================================
  def shutdown
    @writer.close
  end
end
