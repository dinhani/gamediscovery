require 'jrjackson'
require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor
  def stateful?
    false
  end

  def process(content)
    filename, content = content

    # parse type
    type = parse_type(filename)

    # parse json
    data = JrJackson::Json.load(content, :raw => true)

    entities = data.flat_map do |json_element|
      begin
        if type == "companies"
          entity1 = parse_entity("publisher", json_element)
          entity2 = parse_entity("developer", json_element)
          [entity1, entity2]
        else
          parse_entity(type, json_element)
        end
      rescue
        nil
      end
    end.compact

    entities
  end

  # ============================================================================
  # PARSING
  # ============================================================================
  private def parse_entity(type, json_element)
    entity = Entity.new

    entity.id = parse_id(json_element)
    entity.id_specific = parse_id_specific(json_element)
    entity.name = parse_name(json_element)
    entity.attributes = parse_attributes(type, json_element)
    entity.type = type

    entity
  end

  private def parse_type(filename)
    filename.split("_")[0]
  end

  private def parse_id(json_element)
    ID.parse(json_element["name"]).to_s
  end

  private def parse_id_specific(json_element)
    json_element["id"].to_s
  end

  private def parse_name(json_element)
    json_element["name"]
  end

  private def parse_attributes(type, json_element)
    attributes = {}
    if type == "publisher"
      attributes["games"] = json_element["published"]
    elsif type == "developer"
      attributes["games"] = json_element["developed"]
    else
      attributes["games"] = json_element["games"]
    end

    attributes["games"] = (attributes["games"] or [])
    attributes
  end
end
