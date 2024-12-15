
local cursor=0
local id=KEYS[1]
local arg=ARGV[1]
local ttl=ARGV[2]
local number=0

repeat
    local result = redis.call('SCAN',cursor,"MATCH", id)
    cursor = tonumber(result[1])
    for _, key in ipairs(result[2]) do

        if(not(ttl)) then
            redis.call("SET", key, arg)
        else
            redis.call("SETEX", key,ttl, arg)
        end
        number=number +1
    end
until cursor == 0

return number

