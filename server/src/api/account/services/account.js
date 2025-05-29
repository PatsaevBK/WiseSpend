'use strict';

/**
 * account service
 */

const { createCoreService } = require('@strapi/strapi').factories;

module.exports = createCoreService('api::account.account',
    ({ strapi }) => ({

        async sync(ctx) {
            console.log('Request body:', ctx.request.body);

            const user = ctx.state.user
            const body = ctx.request.body.map(item => item.data);
            const type = 'api::account.account'

            const accountsFromDb = await strapi.db.query(type)
                .findMany({
                    filters: {
                        userId: user.id
                    }
                })
            
            const dictAccountsFromDb = Object.fromEntries(accountsFromDb.map(
                c => [c.idLocal, c]
            ))

            const accountsFromClient = body.filter( account =>
                dictAccountsFromDb[account.idLocal] ?
                dictAccountsFromDb[account.idLocal].updatedAtLocal < account.updatedAtLocal : true
            ).map(account => {
                account.userId = user.id
                return account
            })

            const accountsToCreate = accountsFromClient.filter( account =>
                !dictAccountsFromDb[account.idLocal]
            )

            const accountsToUpdate = accountsFromClient.filter( account =>
                dictAccountsFromDb[account.idLocal]
            )

            if(accountsToCreate.length > 0){
                await strapi.db.query(type).createMany({ data: accountsToCreate })
            }

            if(accountsToUpdate.length > 0){ 
                //удалить все категории с таким idLocal
                await strapi.db.query(type).deleteMany({
                    where: {
                        idLocal: {
                            $in: accountsToUpdate.map(c => c.idLocal)
                        }
                    }
                })
                //вставить новые данные
                await strapi.db.query(type).createMany({
                    data : accountsToUpdate
                })
            }
            

            const dictAccountsFromClient = Object.fromEntries(body.map(c => [c.idLocal, c]))

            console.log('Accounts to create:', accountsToCreate);
            console.log('Accounts to update:', accountsToUpdate);
            return accountsFromDb
                .filter(account => dictAccountsFromClient[account.idLocal] ?
                    dictAccountsFromClient[account.idLocal].updatedAtLocal < account.updatedAtLocal : true)
        }
    })
);
