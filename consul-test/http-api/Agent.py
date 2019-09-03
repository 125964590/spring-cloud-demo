import requests

base_url = 'http://192.168.100.47:8500'

params = {""}


def membersList():
    # get members list
    return requests.get(base_url + "/v1/agent/members")


def checkServices():
    # get all services check result
    return requests.get(base_url + "/v1/agent/checks")


def registerServer():
    # register the server to consul
    data = {
        "ID": "mem",
        "Name": "Memory utilization",
        "Notes": "Ensure we don't oversubscribe memory",
        "DeregisterCriticalServiceAfter": "90m",
        "Args": ["/usr/local/bin/check_mem.py"],
        "DockerContainerID": "f972c95ebf0e",
        "Shell": "/bin/bash",
        "HTTP": "https://example.com",
        "Method": "POST",
        "Header": {"x-foo": ["bar", "baz"]},
        "TCP": "example.com:22",
        "Interval": "10s",
        "TTL": "15s"
    }
    return requests.put(base_url + "/v1/agent/check/register", json=data)


def deregisterServer():
    # deregister server
    return requests.put(base_url + "/v1/agent/check/deregister" + "/jbzm-test")


def ttlCheck():
    # check pass
    return requests.put(base_url + "/v1/agent/check/pass" + "/service:base-search-11193-192-168-11-48")


if __name__ == '__main__':
    print(checkServices().text)
