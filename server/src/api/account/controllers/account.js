'use strict';

/**
 * account controller
 */

const { createCoreController } = require('@strapi/strapi').factories;

module.exports = createCoreController('api::account.account',
    ({ strapi }) => ({

        //default
        async find(ctx) {
            const accounts = await strapi.entityService.findMany('api::account.account',
                {
                    filters: {
                        userId: ctx.state.user.id
                    }
                }
            )
            return accounts
        },

        async sync(ctx) {
            return await strapi.service('api::account.account').sync(ctx);
        }
    })
);
