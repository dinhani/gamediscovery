require_relative './Zipper.rb'

class ZipSaver

  attr_reader :zipper

  def initialize(output_file)
    @zipper = Zipper.new(output_file)
  end

  def save(content)
    filename, content = content
    if content.nil?
      return
    end    
    @zipper.add_file(filename, content)
  end

  def shutdown
    @zipper.close
  end
end
