
web sequence diagram syntax (https://www.websequencediagrams.com/#):

title BLAST we page/Web Service interaction

participant BLAST submission page as sub
participant BLAST run page as run
participant BLAST result page as res
participant /megx-blast/jobs as job
participant /megx-blast/jobs/{jobid} as poll

sub-> job: POST
job -> sub: redirect /megx-blast/jobs/{jobid}
sub -> run:

alt job not finished
loop
  run->poll: GET polling job status
  poll->run:still not finished
end

else not succesfull
  poll->run: ha
else succesful
  poll->res:
end