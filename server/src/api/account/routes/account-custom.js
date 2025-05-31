'use strict';

module.exports = { 
    routes: [
        { 
            method: 'POST',
            path: '/accounts/sync',
            handler: 'account.sync'
        }
    ]
};