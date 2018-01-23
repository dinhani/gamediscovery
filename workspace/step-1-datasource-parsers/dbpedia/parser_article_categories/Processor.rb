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
  # PARSING
  # ============================================================================
  # PUBLIC
  def process(line)
    # parse values
    article_id, not_used, category_id = parse_line(line)
    article_id = parse_article_id(article_id)
    category_id = parse_category_id(category_id)

    # check if article changed or still the same
    if @article.id_specific != article_id
      @article = Entity.new
      @article.id = ID::parse(article_id)
      @article.id_specific = article_id
      @changed = true
    else
      @changed = false
    end

    # add categories to article
    @article.categories << category_id

    return @article
  end

  # PRIVATE
  private def parse_line(line)
    line.split(nil, 3)
  end

  private def parse_article_id(article_id)
    article_id.downcase.rpartition("resource/")[-1][0..-2]
  end

  private def parse_category_id(category_id)
    category_id = category_id.downcase.rpartition('category:')[-1][0..-4]    
  end
end
