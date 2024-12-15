
local key = KEYS[1]
local arg=ARGV[1]
local ttl=ARGV[2]
local value = redis.call('GET', key)

if (not (value)) then
    value=tostring(tonumber(arg)-1)
    if(not(ttl)) then
        redis.call("SET", key, value)
    else
        redis.call("SETEX", key,ttl, value)
    end

    return value
end

if (tonumber(value) <0) then
    return -1
end

value=redis.call("DECR",key)

return value