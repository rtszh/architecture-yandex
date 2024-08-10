import { registerApplication, start } from 'single-spa';

registerApplication({
  name: 'user-service',
  app: () => import('userService/userService'),
  activeWhen: ['/user']
});

registerApplication({
  name: 'photo-service',
  app: () => import('photoService/photoService'),
  activeWhen: ['/photo']
});

start();