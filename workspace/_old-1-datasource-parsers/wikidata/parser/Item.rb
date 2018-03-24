require './../../_shared/processing/Entity.rb'

class Item < Entity
    attr_accessor :id_wikipedia, :link, :link_pt

    def initialize
      super
      @id_wikipedia = ""
      @link = ""
      @link_pt = ""
    end
end
