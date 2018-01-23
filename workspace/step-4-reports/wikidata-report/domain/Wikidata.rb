require 'uri'
require 'open-uri'
require 'jrjackson'

class Wikidata

  # ============================================================================
  # QUERIES
  # ============================================================================
  def query_missing(property_id)
    # genetate query
    query = "
    SELECT ?item ?itemLabel ?siteLink
    WHERE {
      ?item wdt:P31 wd:Q7889 .
      ?siteLink schema:about ?item .
      FILTER NOT EXISTS { ?item wdt:#{property_id} ?any } .
      FILTER (SUBSTR(str(?siteLink), 1, 25) = \"https://en.wikipedia.org/\")
      SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }
    }
    LIMIT 20
    "

    # execute query
    execute_query(query)
  end

  # ============================================================================
  # DOWNLOADER
  # ============================================================================
  private def execute_query(query)
    url = generate_url(query)
    response = fetch_url(url)
    json = parse_response(response)

    json
  end

  private def generate_url(query)
    puts "Querying"
    escaped_query = URI::escape(query)
    "https://query.wikidata.org/sparql?format=json&query=#{escaped_query}"
  end

  private def fetch_url(url)
    puts "Downloading"
    open(url) do |response|
      response
    end
  end

  private def parse_response(response)
    puts "Parsing"

    # parse json
    json = JrJackson::Json.load(response)

    # parse items
    items = json["results"]["bindings"].map do |json_item|
      puts json_item
      item = Item.new
      item.id = json_item["item"]["value"].rpartition("/")[-1]
      item.name = json_item["itemLabel"]["value"]
      item.link = json_item["siteLink"]["value"]
      item
    end

    # return
    items
  end
end
