require 'nokogiri'
require 'jrjackson'
require './../../_shared/processing/NLP.rb'
require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor

  def initialize()
    @nlp = NLP::Processor.new
  end

  # ============================================================================
  # STATEFUL
  # ============================================================================
  def stateful?
    false
  end

  # ============================================================================
  # PARSING
  # ============================================================================
  def process(content)
    filename, content = content
    puts filename

    # parse json
    items = JrJackson::Json.load(content, :raw => true)['results']

    # process games in json
    entities = items.map do |json|
      entity = Entity.new

      # parse id
      entity.id = parse_id(json)
      next if entity.id.nil?

      # parse description
      entity.content = parse_description(json)

      # parse aliases
      if filename.start_with? "game"
        entity.aliases = parse_aliases(json)
      else
        entity.aliases = ""
      end

      # parse word_count
      text = Nokogiri::HTML(entity.content).css("p").to_a.map(&:text).join(" ")
      entity.summary, entity.word_count = @nlp.process(text)
      entity.word_count = {} if entity.word_count.nil?

      entity
    end.compact

    # return multiple entities to be saved
    entities
  end

  private def parse_id(json)
    name = json["name"] # game
    name = json["wikiObject"] if name.nil? # review

    ID::parse(name) unless name.nil?
  end

  private def parse_description(json)
    json["description"]
  end

  private def parse_aliases(json)
    aliases = json["aliases"]

    aliases
      .split("\n")
      .map{|a| a.gsub("\n", '').gsub("\r", '').gsub(",", '').gsub("\"", '').gsub("--", '')}
      .select{|a| not a.empty?}.join(",") unless aliases.nil?
  end
end
