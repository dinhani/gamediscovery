require_relative './Pool.rb'

class Executor

  attr_accessor :reader, :processor, :saver, :wait_on, :log_interval

  def initialize
    # services
    @pool = Pool::create(1)

    # data
    @start_time = Time.now
    @counter = 0
    @log_interval = 50000
  end

  # ============================================================================
  # SETTERS
  # ============================================================================
  def threads(threads)
    @pool.resize(threads)
  end

  # ============================================================================
  # EXECUTION
  # ============================================================================
  def execute
    # iterate reader
    until @reader.finished? do
      # read file
      content = read_content()
      next if content.nil?

      # submit to thread processing
      if @processor.stateful?
        execute_steps(content)
      else
        @pool.process content do |content|
          execute_steps(content)
        end

        # wait threads to not overload processor
        @pool.wait_on(@wait_on) unless @wait_on.nil?
      end
    end

    # shutdown everything
    shutdown()
  end

  private def execute_steps(content)
    log()
    processed = process(content)
    save(processed)
  end

  # ============================================================================
  # EXECUTION (STEPS)
  # ============================================================================
  # Log the current ammount of processed content though a predefined interval.
  private def log()
    @counter = @counter + 1
    if @counter % @log_interval == 0
      puts "[#{Time.now - @start_time}] #{@counter}"
    end
  end

  # Read the content of a input using a reader.
  # It can return how many values it wants. The processor and saver should handle the input according.
  private def read_content()
    begin
      return @reader.read()
    rescue Exception => e
      puts "Error reading file: #{e.message}"
      puts e.backtrace

      return nil
    end
  end



  # Process the input using a processor.
  private def process(content)
    begin
      return @processor.process(content)
    rescue Exception => e
      puts "Error processing: #{e.message}"
      puts e.backtrace

      return nil
    end
  end

  # Saves the processed content by the processor unless the processed content is nil
  # If the processor is stateless, it saves the current processed content
  # If the processor is stateful, it saves the previous processed content when the processor signals it finished the previous
  private def save(processed_content)
    begin
      # stateless save
      if not @processor.stateful?
        @saver.save(processed_content) unless processed_content.nil?
        return
      end

      # stateful save
      if @processor.stateful?
        if @processor.changed?
          @saver.save(@previous_processed_content) unless @previous_processed_content.nil?
        end
        @previous_processed_content = processed_content
      end
    rescue Exception => e
      puts "Error saving: #{e.message}"
      puts e.backtrace
    end
  end

  private def shutdown()
    @pool.shutdown()
    @saver.shutdown()
  end
end
