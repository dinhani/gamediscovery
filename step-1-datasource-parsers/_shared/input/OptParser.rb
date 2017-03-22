require 'optparse'
require 'fileutils'

class OptParser

  def initialize()
    @valid = true
  end

  def parse(required_fields)
    # default values
    options = {:input => nil, :output => nil, :skip => 0}

    # do parsing
    OptionParser.new do |opts|
        # input file
        opts.on("-i FILE", "--input FILE", "Set the input file from where the data will be parsed") do |input_file|
            options[:input] = input_file
        end

        # output dir
        opts.on("-o DIR", "--output DIR", "Set the output folder where the parsed data will be saved") do |output_dir|
          options[:output] = output_dir

          # ensure path exists
          dirname = File.dirname(output_dir)
          FileUtils.mkpath dirname
        end

        # threads
        opts.on("-t THREADS", "--threads THREADS", "Set the number of threads that will be used to process the files") do |threads|
            options[:threads] = threads.to_i
        end
    end.parse!

    # check parameters
    if required_fields.include? :input and options[:input].nil?
      puts "Missing parameter: specify the input file using -i <file> parameter"
      @valid = false
    end

    if required_fields.include? :output and options[:output].nil?
      puts "Missing parameter: specify the output directory using the -o <directory> parameter"
      @valid = false
    end

    # return parsed parameters
    if not @valid
      puts "Exiting"
      exit
    end

    puts options
    return options
  end
end
