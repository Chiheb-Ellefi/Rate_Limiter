local list=KEYS[1]
local timestamp=ARGV[1]
local len=ARGV[2]
local time=ARGV[3]



local length=redis.call("LLEN", list)
local value =tonumber(len)-tonumber(length)
    if(value <0 ) then
        local first=redis.call("LINDEX",list,0)
        if(tonumber(timestamp)-tonumber(first)<tonumber(time)) then
            return false
        end
        redis.call("LPOP",list)
    end
redis.call("RPUSH",list,timestamp)
return true




