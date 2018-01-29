require './../../_shared/processing/Entity.rb'
require './../../_shared/processing/ID.rb'

class Processor

  def initialize
    @category = Entity.new
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
    category_id, not_used, other_category_id = parse_line(line)
    category_id = parse_category_id(category_id)
    other_category_id = parse_other_category_id(other_category_id)

    # check if article changed or still the same
    if @category.id_specific != category_id
      @category = Entity.new
      @category.id = ID::parse(category_id)
      @category.id_specific = category_id
      @changed = true
    else
      @changed = false
    end

    # add categories to article
    if not other_category_id.nil?
      @category.categories << other_category_id
    end

    return @category
  end

  # PRIVATE
  private def parse_line(line)
    line.split(nil, 3)
  end

  private def parse_category_id(category_id)
    match = category_id.downcase.match('.*category:(.+)>').captures[0]
  end

  private def parse_other_category_id(other_category_id)
    match = other_category_id.downcase.match('.*category:(.+)>')
    (not match.nil?) ? match.captures[0] : nil
  end
end
