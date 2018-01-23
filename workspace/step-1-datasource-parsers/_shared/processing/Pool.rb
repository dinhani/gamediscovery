require 'thread/pool'

class Pool
  def self.create(threads)
    pool = Thread.pool(threads)

    # create a method to wait submission of new tasks
    def pool.wait_on(max_number_of_tasks)
      if backlog == max_number_of_tasks
        while backlog > 10
          sleep(1)
        end
      end
    end
    return pool
  end
end
