import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { useAuthGuard } from "@/hooks/useAuthGuard";
import { Separator } from "@/components/ui/separator";
import {
  Calendar,
  Clock,
  FileText,
  Heart,
  Activity,
  Pill,
  Phone,
  Mail,
  User,
  MapPin,
  Bell,
  Settings,
  LogOut,
  CalendarPlus,
  MessageSquare,
  Download,
} from "lucide-react";
import { useNavigate } from "react-router-dom";

const PacienteHome = () => {
  useAuthGuard();
  const [patientName] = useState("Nome do Paciente"); // Em um app real, isso viria do contexto de auth
  const navigate = useNavigate();

  const upcomingAppointments = [
    {
      id: 1,
      doctor: "Dr. Maria Santos",
      specialty: "Cardiologia",
      date: "2024-03-15",
      time: "14:30",
      status: "Confirmado",
    },
    {
      id: 2,
      doctor: "Dr. Pedro Lima",
      specialty: "Clínico Geral",
      date: "2024-03-22",
      time: "09:00",
      status: "Pendente",
    },
  ];

  const recentExams = [
    {
      id: 1,
      name: "Hemograma Completo",
      date: "2024-03-01",
      doctor: "Dr. Ana Costa",
      status: "Disponível",
    },
    {
      id: 2,
      name: "Eletrocardiograma",
      date: "2024-02-28",
      doctor: "Dr. Maria Santos",
      status: "Disponível",
    },
  ];

  const medications = [
    {
      id: 1,
      name: "Losartana 50mg",
      dosage: "1 comprimido",
      frequency: "1x ao dia",
      nextDose: "08:00",
    },
    {
      id: 2,
      name: "Sinvastatina 20mg",
      dosage: "1 comprimido",
      frequency: "1x ao dia",
      nextDose: "20:00",
    },
  ];

  const vitalSigns = {
    bloodPressure: "120/80 mmHg",
    heartRate: "75 bpm",
    weight: "78 kg",
    height: "1.75 m",
    lastUpdate: "2024-03-01",
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-gray-50 to-blue-100">
      {/* Header */}
      <header className="bg-white/95 backdrop-blur-md border-b border-blue-200/20 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center space-x-4">
              <div className="bg-blue-600 rounded-full p-2 shadow-md">
                <Heart className="h-6 w-6 text-white" />
              </div>
              <h1 className="text-xl font-bold text-blue-700">AVICIA</h1>
            </div>
            <div className="flex items-center space-x-4">
              <Button variant="ghost" size="sm" className="hover:bg-blue-50">
                <Bell className="h-5 w-5 text-gray-600" />
              </Button>
              <Button variant="ghost" size="sm" className="hover:bg-blue-50">
                <Settings className="h-5 w-5 text-gray-600" />
              </Button>
              <Button
                variant="ghost"
                size="sm"
                onClick={() => {
                  localStorage.removeItem("token");
                  localStorage.removeItem("expiresIn");
                  navigate("/login");
                }}
                className="hover:bg-blue-50"
              >
                <LogOut className="h-5 w-5 text-gray-600" />
              </Button>
              <Avatar className="border border-blue-200">
                <AvatarFallback className="bg-blue-100 text-blue-700">
                  JS
                </AvatarFallback>
              </Avatar>
            </div>
          </div>
        </div>
      </header>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Welcome Section */}
        <div className="mb-8">
          <h2 className="text-3xl font-bold text-blue-700 mb-2">
            Olá, {patientName}! 👋
          </h2>
          <p className="text-gray-600">
            Acompanhe sua saúde de forma simples e organizada
          </p>
        </div>

        {/* Quick Actions */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-4 mb-8">
          <Button className="h-16 bg-gradient-to-r from-blue-600 to-teal-500 hover:from-blue-700 hover:to-teal-600 text-white rounded-lg shadow-md hover:shadow-lg transition-all duration-300">
            <CalendarPlus className="h-5 w-5 mr-2" />
            Agendar Consulta
          </Button>
          <Button
            variant="outline"
            className="h-16 border-blue-200 text-blue-600 hover:bg-blue-50 hover:text-blue-700 rounded-lg transition-all duration-300"
          >
            <MessageSquare className="h-5 w-5 mr-2" />
            Falar com Médico
          </Button>
          <Button
            variant="outline"
            className="h-16 border-blue-200 text-blue-600 hover:bg-blue-50 hover:text-blue-700 rounded-lg transition-all duration-300"
          >
            <Download className="h-5 w-5 mr-2" />
            Meus Exames
          </Button>
          <Button
            variant="outline"
            className="h-16 border-blue-200 text-blue-600 hover:bg-blue-50 hover:text-blue-700 rounded-lg transition-all duration-300"
          >
            <FileText className="h-5 w-5 mr-2" />
            Receitas
          </Button>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Left Column */}
          <div className="lg:col-span-2 space-y-6">
            {/* Próximas Consultas */}
            <Card className="shadow-lg border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-xl">
              <CardHeader>
                <CardTitle className="flex items-center text-blue-700">
                  <Calendar className="h-5 w-5 mr-2" />
                  Próximas Consultas
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {upcomingAppointments.map((appointment) => (
                    <div
                      key={appointment.id}
                      className="flex items-center justify-between p-4 bg-blue-50 rounded-lg border border-blue-100 hover:bg-blue-100 transition-colors duration-200"
                    >
                      <div className="flex-1">
                        <h4 className="font-medium text-gray-800">
                          {appointment.doctor}
                        </h4>
                        <p className="text-sm text-gray-600">
                          {appointment.specialty}
                        </p>
                        <div className="flex items-center mt-2 text-sm text-gray-600">
                          <Calendar className="h-4 w-4 mr-1" />
                          {new Date(appointment.date).toLocaleDateString(
                            "pt-BR"
                          )}
                          <Clock className="h-4 w-4 ml-3 mr-1" />
                          {appointment.time}
                        </div>
                      </div>
                      <Badge
                        variant={
                          appointment.status === "Confirmado"
                            ? "default"
                            : "secondary"
                        }
                        className={
                          appointment.status === "Confirmado"
                            ? "bg-teal-500 text-white"
                            : "bg-gray-200 text-gray-700"
                        }
                      >
                        {appointment.status}
                      </Badge>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>

            {/* Exames Recentes */}
            <Card className="shadow-lg border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-xl">
              <CardHeader>
                <CardTitle className="flex items-center text-blue-700">
                  <FileText className="h-5 w-5 mr-2" />
                  Exames Recentes
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {recentExams.map((exam) => (
                    <div
                      key={exam.id}
                      className="flex items-center justify-between p-4 bg-blue-50 rounded-lg border border-blue-100 hover:bg-blue-100 transition-colors duration-200"
                    >
                      <div className="flex-1">
                        <h4 className="font-medium text-gray-800">
                          {exam.name}
                        </h4>
                        <p className="text-sm text-gray-600">
                          Dr. {exam.doctor}
                        </p>
                        <p className="text-sm text-gray-600">
                          {new Date(exam.date).toLocaleDateString("pt-BR")}
                        </p>
                      </div>
                      <div className="flex items-center space-x-2">
                        <Badge className="bg-teal-500 text-white">
                          {exam.status}
                        </Badge>
                        <Button
                          size="sm"
                          variant="outline"
                          className="border-blue-200 text-blue-600 hover:bg-blue-50"
                        >
                          <Download className="h-4 w-4" />
                        </Button>
                      </div>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>

          <div className="space-y-6">
            {/* Sinais Vitais */}
            <Card className="shadow-lg border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-xl">
              <CardHeader>
                <CardTitle className="flex items-center text-blue-700">
                  <Activity className="h-5 w-5 mr-2" />
                  Sinais Vitais
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-600">
                      Pressão Arterial
                    </span>
                    <span className="font-medium text-gray-800">
                      {vitalSigns.bloodPressure}
                    </span>
                  </div>
                  <Separator className="bg-blue-100" />
                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-600">
                      Frequência Cardíaca
                    </span>
                    <span className="font-medium text-gray-800">
                      {vitalSigns.heartRate}
                    </span>
                  </div>
                  <Separator className="bg-blue-100" />
                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-600">Peso</span>
                    <span className="font-medium text-gray-800">
                      {vitalSigns.weight}
                    </span>
                  </div>
                  <Separator className="bg-blue-100" />
                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-600">Altura</span>
                    <span className="font-medium text-gray-800">
                      {vitalSigns.height}
                    </span>
                  </div>
                  <p className="text-xs text-gray-600 mt-4">
                    Última atualização:{" "}
                    {new Date(vitalSigns.lastUpdate).toLocaleDateString(
                      "pt-BR"
                    )}
                  </p>
                </div>
              </CardContent>
            </Card>

            {/* Medicamentos */}
            <Card className="shadow-lg border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-xl">
              <CardHeader>
                <CardTitle className="flex items-center text-blue-700">
                  <Pill className="h-5 w-5 mr-2" />
                  Medicamentos
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {medications.map((medication) => (
                    <div
                      key={medication.id}
                      className="p-3 bg-blue-50 rounded-lg border border-blue-100 hover:bg-blue-100 transition-colors duration-200"
                    >
                      <h4 className="font-medium text-sm text-gray-800">
                        {medication.name}
                      </h4>
                      <p className="text-xs text-gray-600">
                        {medication.dosage} - {medication.frequency}
                      </p>
                      <div className="flex items-center mt-2 text-xs text-gray-600">
                        <Clock className="h-3 w-3 mr-1" />
                        Próxima dose: {medication.nextDose}
                      </div>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>

            {/* Informações do Paciente */}
            <Card className="shadow-lg border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-xl">
              <CardHeader>
                <CardTitle className="flex items-center text-blue-700">
                  <User className="h-5 w-5 mr-2" />
                  Minhas Informações
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-3 text-sm">
                  <div className="flex items-center">
                    <Mail className="h-4 w-4 mr-2 text-gray-600" />
                    xxxxxx.xxxx@gmail.com
                  </div>
                  <div className="flex items-center">
                    <Phone className="h-4 w-4 mr-2 text-gray-600" />
                    (81) 00000000
                  </div>
                  <div className="flex items-center">
                    <MapPin className="h-4 w-4 mr-2 text-gray-600" />
                    Recife-PE
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PacienteHome;
