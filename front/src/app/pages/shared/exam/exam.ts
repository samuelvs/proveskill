import { Question } from "../question/question";

export class Exam {
  id: number = null;
  title: string = '';
  startDateTime: Date;
  endDateTime: Date;
  duration: number = null;
  questions: Question[] = [];
}
