export interface Intern {
    id?: number;
    name: string;
    time_period: string;
    total_Amount?: number;
    startDate: string; // Use string to handle date in ISO format
    paidAmount?: number;
    status?: boolean;
  }
  