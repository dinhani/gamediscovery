require 'jrjackson'
require 'andand'
require './../../_shared/processing/ID.rb'
require './Item.rb'
require './Attribute.rb'

class Processor

  def stateful?
    false
  end

  def process(line)
    # transform in json
    json = JrJackson::Json.load(line, :raw => true)

    # delegate parsing
    if(json['type'] == 'item')
        process_item(json)
    else
        process_attribute(json)
    end
  end

  # ============================================================================
  # ITEM
  # ============================================================================
  private def process_item(json)
    item = Item.new

    # basic info

    # name
    item.name =  json['labels'].andand['en'].andand['value']
    return if item.name.nil?

    #link
    link = json['sitelinks'].andand['enwiki'].andand['title']
    item.link = link.gsub(" ", "_") unless link.nil?

    link_pt = json['sitelinks'].andand['ptwiki'].andand['title']
    item.link_pt = link_pt.gsub(" ", "_") unless link_pt.nil?

    # ids
    item.id = ID::parse(item.name)
    item.id_specific = json['id']
    item.id_wikipedia = item.link.downcase unless link.nil?

    # attributes
    claims = json['claims']
    attribute_keys = claims.keys

    attribute_keys.map do |attribute|
        item.attributes[attribute] = claims[attribute].map do |claim|
            mainsnak = claim['mainsnak']

            # value
            if mainsnak['snaktype'] == 'value'
                datavalue = mainsnak['datavalue']
                # entity
                type = datavalue['type']
                value = datavalue['value']

                if type == 'wikibase-entityid'
                    "Q" +  value['numeric-id'].to_s
                elsif type == 'time'
                    value['time'].to_s
                elsif type == 'quantity'
                    value['amount'].to_s
                else
                    value.to_s
                end
            # novalue
            else
                ""
            end
        end
    end

    # if has id and name, can save, else do not save
    if not item.id.nil? and not item.name.nil?
      item
    else
      nil
    end
  end

  # ============================================================================
  # Attribute
  # ============================================================================
  private def process_attribute(json)
    attribute = Attribute.new
    attribute.id = json['id']
    attribute.name = json['labels']['en']['value']

    attribute
  end
end
