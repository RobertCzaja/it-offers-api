db = db.getSiblingDB('it-offers-dev');
db.createUser(
    {
        user: "admin",
        pwd: "admin",
        roles: [{role: "readWrite", db: "it-offers-dev"}]
    }
);

db = db.getSiblingDB('it-offers-integration-tests');
db.createUser(
    {
        user: "admin",
        pwd: "admin",
        roles: [{role: "readWrite",db: "it-offers-integration-tests"}]
    }
);