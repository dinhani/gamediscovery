require 'zip'
require 'thread/every'
require 'thread/pool'

class Zipper
  def initialize(output_file)
    # create file
    Zip.unicode_names = true
    @zip = Zip::File.open(output_file, Zip::File::CREATE)
    @zip.commit

    # create pool to save zips
    @mutex = Mutex.new
    @pool = Thread.pool(1)

    # create commiter to add files to zip
    @commiter = Thread.every(10) do
      @mutex.synchronize do
        @zip.commit
      end
    end
  end

  def add_file(filename, content)
    @pool.process filename, content do |filename, content|
      @mutex.synchronize do
        @zip.get_output_stream(filename) do |out|
          out.write content
        end
      end
    end
  end

  def exist?(filename)
    @zip.find_entry(filename) != nil
  end

  def close
    @pool.shutdown
    @commiter.cancel
    @zip.commit
  end
end
