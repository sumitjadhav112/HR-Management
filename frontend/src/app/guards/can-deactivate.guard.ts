import { CanDeactivateFn } from '@angular/router';
import { ClientRegistrationComponent } from '../Client/client-registration/client-registration.component';

export const canDeactivateGuard: CanDeactivateFn<ClientRegistrationComponent> = (component, currentRoute, currentState, nextState) => {
  if (component.ClientRegisterData.dirty) {
    return window.confirm('You have unsaved changes! Do you really want to leave?');
  }
  return true;
};
