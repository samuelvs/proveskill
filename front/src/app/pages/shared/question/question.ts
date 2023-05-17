export class Question {
  id: number = null;
  name: string = '';
  level: number = null;
  tags: string[] = [''];
  type: number = null;
  alternatives?: string[] = [''];
  answer?: any;
}
