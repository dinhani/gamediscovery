require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor

  def initialize
    @article = Entity.new
    @changed = false
  end

  # ============================================================================
  # STATEFUL INDICATOR
  # ============================================================================
  def stateful?
    true
  end

  def changed?
    @changed
  end

  # ============================================================================
  # PARSING (GENERAL)
  # ============================================================================
  def process(line)
    # parse values
    article_id, attribute_key, attribute_value = parse_line(line)

    article_id = parse_article_id(article_id)
    attribute_value = parse_attribute_value(attribute_value)
    attribute_key = parse_attribute_key(attribute_key)

    # check if is a new article
    if @article.id_specific != article_id
      @article = Entity.new
      @article.id = ID::parse(article_id)
      @article.id_specific = article_id
      @changed = true
    else
      @changed = false
    end

    # add attributes to article
    if not @article.attributes.has_key? attribute_key
      @article.attributes[attribute_key] = []
    end

    @article.attributes[attribute_key] << attribute_value
    return @article
  end

  # ============================================================================
  # PARSING (SPECIFIC)
  # ============================================================================
  private def parse_line(line)
    line.split(nil, 3)
  end

  private def parse_article_id(article_id)
    article_id.split('/')[-1][0..-2].downcase
  end

  private def parse_attribute_key(attribute_key)
    attribute_key.split('/')[-1][0..-2].downcase
  end

  private def parse_attribute_value(attribute_value)
    attribute_value = attribute_value.gsub("\"", "")

    if(attribute_value.include? "@en")
      return attribute_value.split("@en")[0]
    end

    if(attribute_value.include? "^^")
      return attribute_value.split("^^")[0]
    end

    if(attribute_value.include? "/resource")
      return attribute_value.split('/')[-1][0..-2].downcase
    end

    attribute_value
  end
end
