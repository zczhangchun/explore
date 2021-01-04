local rst={};
for i,v in pairs(KEYS) do rst[i]=redis.call('hget', v, ARGV[1]) end;
return rst